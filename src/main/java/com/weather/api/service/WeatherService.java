package com.weather.api.service;

import com.weather.service.dto.AccountDto;
import com.weather.service.dto.TransactionDto;

import java.util.List;

public interface WeatherService {
    /*This will retrieve the accounts corresponding to customerid*/
    List<AccountDto> getAccounts(String custID);

    /*This will retrieve the transactions details for the account*/
    List<TransactionDto> getTransactions(String accountNum);

}
