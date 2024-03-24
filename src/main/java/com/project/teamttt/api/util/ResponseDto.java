package com.project.teamttt.api.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ResponseDto<T>  {
    private boolean success;
    private String message;
    private T data;
}

