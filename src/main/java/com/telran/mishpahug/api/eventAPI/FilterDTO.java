package com.telran.mishpahug.api.eventAPI;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class FilterDTO {
    private String dateFrom;
    private String dateTo;
    private String holidays;
    private String confession;
    private String food;
}


