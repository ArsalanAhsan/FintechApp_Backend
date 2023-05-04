package com.iconsult.services;


import com.iconsult.entities.AccountDetails;
import com.iconsult.repository.AccountDetailsRepository;
import com.iconsult.zeenebel.Constants.statusResponse;
import com.iconsult.zeenebel.accountservice.*;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@GrpcService
public class AccountService extends AccountServicesGrpc.AccountServicesImplBase {


    @Autowired
    private AccountDetailsRepository accountDetailsRepository;


    @Override
    public void getAccountList(accoutsSearchRequest request, StreamObserver<accountsSearchResponse> responseObserver) {
        accountsSearchResponse.Builder builder = accountsSearchResponse.newBuilder();
        List<AccountDetails> accountDetails = this.accountDetailsRepository.findAllByCnic(request.getCnic());
        List<accountDto> list = new ArrayList<>();

        if (this.accountDetailsRepository.existsByCnic(request.getCnic())) {
            {
                list = accountDetails.stream().map(account -> {
                    accountDto accountdto = accountDto.newBuilder().setAccountNum(String.valueOf(account.getAccountNum()))
                            .setAccType(AccounType.valueOf(account.getAccType().toUpperCase()))
                            .setBankName(account.getBankName())
                            .setBranchId(account.getBranchId())
                            .setCnic(account.getCnic())
                            .build();
                    return accountdto;
                }).collect(Collectors.toList());
                builder.addAllAccountList(list).setDescription("Following accounts are registered in database").setStatResp(statusResponse.Record_found_Succesfully);

            }
        } else {
            builder.setStatResp(statusResponse.No_Record_Found).setDescription("No account is registered on following cnic");

        }

        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();

    }

    @Override
    public void showAccountDetails(showAccountDetailsRequest request, StreamObserver<showAccountDetailsResponse> responseObserver) {
        showAccountDetailsResponse.Builder builder = showAccountDetailsResponse.newBuilder();
        if (this.accountDetailsRepository.existsByAccountNum(request.getAccountNum())) {
            builder.setStatResp(statusResponse.SignIn_Successfully);
           AccountDetails account =this.accountDetailsRepository.findByAccountNum(request.getAccountNum());

            accountDto accountdto = accountDto.newBuilder().setAccountNum(String.valueOf(account.getAccountNum()))
                    .setAccType(AccounType.valueOf(account.getAccType().toUpperCase()))
                    .setBankName(account.getBankName())
                    .setBranchId(account.getBranchId())
                    .setCnic(account.getCnic())
                    .build();
builder.setAccount(accountdto).setStatResp(statusResponse.Record_found_Succesfully);

        }else {

            builder.setStatResp(statusResponse.No_Record_Found);
        }

        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }


}
