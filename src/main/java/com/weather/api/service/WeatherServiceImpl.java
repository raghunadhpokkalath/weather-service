package com.weather.api.service;

import com.weather.service.dto.AccountDto;
import com.weather.service.dto.TransactionDto;
import com.weather.service.exception.RecordNotFoundException;
import com.weather.service.model.Account;
import com.weather.service.model.Transaction;
import com.weather.service.repository.AccountRepository;
import com.weather.service.repository.TransactionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WeatherServiceImpl implements WeatherService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    ModelMapper modelMapper;


    @Override
    public List<AccountDto> getAccounts(String custID) {

        List<Account> accountList = accountRepository.findByCustomerCustomerId(custID);
        if (!accountList.isEmpty()){
           return accountList.stream().map(account -> modelMapper.map(account,AccountDto.class)).collect(Collectors.toList());
        }

       throw new RecordNotFoundException("No accounts for the customer");
    }

    @Override
    public List<TransactionDto> getTransactions(String  accountNum) {

        List<Transaction> transactionList = transactionRepository.findByAccountAccountNumber(accountNum);

        if (!transactionList.isEmpty()){
            return transactionList.stream().map(transaction -> modelMapper.map(transaction,TransactionDto.class)).collect(Collectors.toList());
        }
        throw new RecordNotFoundException("No transactions for the account");
    }
}
