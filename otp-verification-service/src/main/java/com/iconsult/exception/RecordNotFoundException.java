package com.iconsult.exception;

import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;

import javax.annotation.Nullable;

public class RecordNotFoundException extends StatusRuntimeException {

    String status;
    String value;

    public RecordNotFoundException(Status status) {
        super(status);
    }

    public RecordNotFoundException(Status status, @Nullable Metadata trailers) {
        super(status, trailers);
    }


}
