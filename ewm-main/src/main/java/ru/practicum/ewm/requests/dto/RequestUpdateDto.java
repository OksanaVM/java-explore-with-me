package ru.practicum.ewm.requests.dto;

import lombok.Value;

import java.util.List;

@Value
public class RequestUpdateDto {
    List<RequestDto> confirmedRequests;
    List<RequestDto> rejectedRequests;
}
