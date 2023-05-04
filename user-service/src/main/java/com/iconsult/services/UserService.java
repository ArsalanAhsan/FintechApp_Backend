package com.iconsult.services;

import com.iconsult.entities.BeneficiariesAccounts;
import com.iconsult.entities.User;
import com.iconsult.entities.Users_Temp;
import com.iconsult.repository.BeneficiariesAccountsRepository;
import com.iconsult.repository.UserRepository;
import com.iconsult.repository.UserTempRepository;
import com.iconsult.zeenebel.Constants.statusResponse;
import com.iconsult.zeenebel.accountservice.AccountServicesGrpc;
import com.iconsult.zeenebel.accountservice.showAccountDetailsRequest;
import com.iconsult.zeenebel.accountservice.showAccountDetailsResponse;
import com.iconsult.zeenebel.userservice.*;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.client.inject.GrpcClient;
import net.devh.boot.grpc.server.service.GrpcService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@GrpcService
public class UserService extends UserServicesGrpc.UserServicesImplBase {


    @Autowired
    private UserTempRepository userTempRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private BeneficiariesAccountsRepository beneficiariesAccountsRepository;
    @GrpcClient("account-service")
    private AccountServicesGrpc.AccountServicesBlockingStub accountServicesBlockingStub;

    private static void accept(Object t) {
        System.out.println(t.toString());
    }


    @Override
    public void showAllBeneficiary(showAllBeneficiaryRequest request, StreamObserver<showAllBeneficiaryResponse> responseObserver) {

        showAllBeneficiaryResponse.Builder response = showAllBeneficiaryResponse.newBuilder();
        BeneficiaryRequestDTO.Builder beneficiaryRequestDTO = BeneficiaryRequestDTO.newBuilder();
        this.beneficiariesAccountsRepository.findAllByUserId(String.valueOf(request.getUserId())).ifPresentOrElse(
                t -> {
                    List<BeneficiaryRequestDTO> requestDTOList = t.stream().map(acc -> BeneficiaryRequestDTO.newBuilder()
                                    .setBeneficiaryId(acc.getBeneficiaryId())
                                    .setUserId(acc.getUserId())
                                    .setAccountNum(acc.getAccountNum()).setBank(acc.getBank())
                                    .setAlias(acc.getAlias()).build())
                            .collect(Collectors.toList());
           response.addAllShowAllDetails(requestDTOList).setStat(statusResponse.Record_found_Succesfully).build();

                }, () -> {
                    response.setStat(statusResponse.No_Record_Found).build();

                }
        );
        responseObserver.onNext(response.build());
        responseObserver.onCompleted();

    }

    @Override
    public void showBeneficiary( showBeneficiaryRequest request, StreamObserver<showBeneficiaryResponse> responseObserver) {
        showBeneficiaryResponse.Builder response = showBeneficiaryResponse.newBuilder();
        this.beneficiariesAccountsRepository.findById(request.getBeneficiaryId()).ifPresentOrElse(t ->
                {
                    response.setStat(statusResponse.Record_found_Succesfully)
                            .setShowDetails(BeneficiaryRequestDTO.newBuilder()
                                    .setBeneficiaryId(t.getBeneficiaryId())
                                    .setUserId(t.getUserId())
                                    .setAccountNum(t.getAccountNum()).setBank(t.getBank())
                                    .setAlias(t.getAlias()).build()
                            )
                            .build();
                }, () -> {
                    response.setStat(statusResponse.No_Record_Found).build();
                }
        );
        responseObserver.onNext(response.build());
        responseObserver.onCompleted();
    }

    @Override
    public void addBeneficiary(addBeneficiaryRequest request, StreamObserver<userResponse> responseObserver) {
        userResponse.Builder builder = userResponse.newBuilder();

        showAccountDetailsRequest accountDetailsRequest = showAccountDetailsRequest.newBuilder().setAccountNum(String.valueOf(request.getAccountNum())).build();
        showAccountDetailsResponse accountDetailsResponse = this.accountServicesBlockingStub.showAccountDetails(accountDetailsRequest);
        Predicate<showAccountDetailsResponse> p1 = (resp) -> resp.getStatResp().equals(statusResponse.Record_found_Succesfully);
        Predicate<showAccountDetailsResponse> p2 = (resp) -> resp.getAccount().getBankName().equals(request.getBank());
        Predicate<addBeneficiaryRequest> p3 = (resp) -> this.userRepository.existsById(Integer.valueOf(resp.getUserId()));
        BiPredicate<String, String> p4 = (userId, accountId) -> !this.beneficiariesAccountsRepository.existsByAccountNumAndUserId(userId, accountId).isPresent();

        if (p1.and(p2).test(accountDetailsResponse) && p3.test(request) && p4.test(request.getAccountNum(), request.getUserId())) {
            BeneficiariesAccounts beneficiariesAccounts = this.modelMapper.map(request, BeneficiariesAccounts.class);
            this.beneficiariesAccountsRepository.save(beneficiariesAccounts);
            builder.setStat(statusResponse.Account_Registered_Successfully)
                    .setDescription("Verify Your OTP");
        } else {
            if (!p1.and(p2).test(accountDetailsResponse) && p3.test(request)) {
                builder.setStat(statusResponse.No_Record_Found)
                        .setDescription("wrong details inserted check accountNum and BankId information");

            } else if (!p4.test(request.getAccountNum(), request.getUserId())) {
                builder.setStat(statusResponse.Account_Registration_failed)
                        .setDescription("Account already existed in beneficiary list");
            }


        }


//
//
//            if (accountDetailsResponse.getStatResp() == statusResponse.Record_found_Succesfully) {
//            if (accountDetailsResponse.getAccount().getBankName().equals(request.getBank())) {
//
//                if(this.userRepository.existsById(Integer.valueOf(request.getUserId()))){
//                    if (this.beneficiariesAccountsRepository.existsByAccountNumAndUserId(request.getAccountNum(), String.valueOf(request.getUserId())).isPresent()){
//                        builder.setStat(statusResponse.Account_Registration_failed)
//                                .setDescription("Account already existed in beneficiary list");
//                    }else {
//                    BeneficiariesAccounts beneficiariesAccounts=this.modelMapper.map(request,BeneficiariesAccounts.class);
//                    this.beneficiariesAccountsRepository.save(beneficiariesAccounts);
//                    builder.setStat(statusResponse.Account_Registered_Successfully)
//                            .setDescription("Verify Your OTP");}
//                }else{
//                    builder.setStat(statusResponse.No_Record_Found)
//                            .setDescription("wrong details inserted check your user_id information");
//                }
//
//            } else {
//                builder.setStat(statusResponse.No_Record_Found)
//                        .setDescription("wrong details inserted check your bank information");
//
//            }
//
//
//        } else {
//
//            builder.setStat(statusResponse.No_Record_Found)
//                    .setDescription("wrong details inserted");
//        }
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();

    }


    @Override
    public void userSignUp( userDto request, StreamObserver<userResponse> responseObserver) {
        userResponse.Builder builder = userResponse.newBuilder();


        if (this.userTempRepository.existsByCnic(request.getCnic())) {
            builder = builder.setStat(statusResponse.Account_Registration_failed)
                    .setDescription("Cnic Already Registered");
        } else {

            this.userTempRepository.save(this.modelMapper.map(request, Users_Temp.class));

            builder.setStat(statusResponse.Account_Registered_Successfully)
                    .setDescription("Verify Your OTP");
        }

        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void userSignIn(userSignInRequest request, StreamObserver<userResponse> responseObserver) {
        userResponse.Builder builder = userResponse.newBuilder();
        if (request.hasCcr()) {
            this.userRepository.findByCnic(String.valueOf(request.getCcr().getCnicNumber()))
                    .ifPresentOrElse(user -> {
                                if (user.getPassword().equals(request.getCcr().getPassword())) {
                                    builder.setStat(statusResponse.SignIn_Successfully).setDescription("verify your otp");

                                } else {
                                    builder.setStat(statusResponse.Wrong_Id_Password).setDescription("Wrong password");

                                }

                            },
                            () -> {
                                builder.setStat(statusResponse.Wrong_Id_Password).setDescription("NO SUCH User Exist");
                            }

                    );
        } else if (request.hasPcr()) {
            this.userRepository.findByPhoneNumber(String.valueOf(request.getPcr().getPhoneNumber()))
                    .ifPresentOrElse(user -> {
                                if (user.getPassword().equals(request.getPcr().getPassword())) {
                                    builder.setStat(statusResponse.SignIn_Successfully).setDescription("verify your otp");

                                } else {
                                    builder.setStat(statusResponse.Wrong_Id_Password).setDescription("Wrong password");

                                }

                            },
                            () -> {
                                builder.setStat(statusResponse.Wrong_Id_Password).setDescription("NO SUCH User Exist");
                            }

                    );
        } else if (request.hasEcr()) {
            this.userRepository.findByEmail(request.getEcr().getEmailId())
                    .ifPresentOrElse(user -> {
                                if (user.getPassword().equals(request.getEcr().getPass())) {
                                    builder.setStat(statusResponse.SignIn_Successfully).setDescription("verify your otp");

                                } else {
                                    builder.setStat(statusResponse.Wrong_Id_Password).setDescription("Wrong password");

                                }

                            },
                            () -> {
                                builder.setStat(statusResponse.Wrong_Id_Password).setDescription("NO SUCH User Exist");
                            }

                    );
        }

        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();

    }

    @Override
    public void showUsers(showUserRequest request, StreamObserver<showUserResponse> responseObserver) {
        showUserResponse.Builder builder = showUserResponse.newBuilder();
        List<User> users = this.userRepository.findAll();
        List<userDto> list = new ArrayList<>();

        for (int i = 0; i < users.size(); i++) {
            userDto userdto = userDto.newBuilder().setCnic(users.get(i).getCnic())
                    .setDOB(users.get(i).getDOB()).setEmail(users.get(i).getEmail())
                    .setFirstName(users.get(i).getFirstName())
                    .setLastName(users.get(i).getLastName())
                    .setPhoneNumber(users.get(i).getPhoneNumber())
                    .setPassword(users.get(i).getPassword()).build();
            list.add(userdto);
        }
        System.out.println(users.toString());
        builder.addAllUsers(list).build();
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();


    }


}
