package com.multipleDB.createmultipleDB.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmployeeDTO {
    private long id;
    private String  password;
    private String employeeName;
}
