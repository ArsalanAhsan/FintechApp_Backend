package com.iconsult.service.smsserviceimpl;
import com.iconsult.config.TwilioConfig;
import com.iconsult.constant.OtpSource;
import com.iconsult.entity.Otp;
import com.iconsult.payload.OtpDtoRequest;
import com.iconsult.payload.OtpDtoResponse;
import com.iconsult.payload.OtpStatus;
import com.iconsult.repository.OtpRepository;
import com.iconsult.service.TwilioOTPService;
import com.iconsult.utility.Util;
import com.twilio.sdk.creator.api.v2010.account.MessageCreator;
import com.twilio.sdk.resource.api.v2010.account.Message;
import com.twilio.sdk.type.PhoneNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TwilioOTPServiceImpl implements TwilioOTPService {

    @Autowired
    private OtpRepository otpRepository;

    private final static Logger LOGGER = LoggerFactory.getLogger(TwilioOTPServiceImpl.class);

    private OtpDtoResponse otpDtoResponse;



    private final TwilioConfig twilioConfig;

    @Autowired
    public TwilioOTPServiceImpl(TwilioConfig twilioConfig) {
        this.twilioConfig = twilioConfig;
    }

    @Override
    public OtpDtoResponse generateOtp(OtpDtoRequest otpDtoRequest) {

        try {
            PhoneNumber to = new PhoneNumber(otpDtoRequest.getMobileNumber());
            PhoneNumber from = new PhoneNumber(twilioConfig.getTrialNumber());
            String accSid = twilioConfig.getAccountSid();

            String otp = Util.generateOtp();

            Otp user = new Otp();
            user.setOtpCode(otp);
            user.setMobileNumber(to.toString());
            user.setOtpSource(OtpSource.AUTHORIZATION);
            user.setTimestamp(System.currentTimeMillis());
            this.otpRepository.save(user);

            String body = "Dear Customer, we have sent you otp " +otp+". Use this passcode for authentication";
            MessageCreator message = Message.create(accSid, to, from, body);
            message.execute();
            LOGGER.info("Send Otp {} ", otpDtoRequest);
            otpDtoResponse = new OtpDtoResponse(OtpStatus.DELIVERED,body);

        } catch (Exception ex) {
            otpDtoResponse = new OtpDtoResponse(OtpStatus.FAILED, ex.getMessage());
        }

        return otpDtoResponse;
    }





}
