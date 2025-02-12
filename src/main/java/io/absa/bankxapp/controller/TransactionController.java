package io.absa.bankxapp.controller;

import io.absa.bankxapp.model.Transaction;
import io.absa.bankxapp.service.TransactionService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RequestMapping("/api/transactions")
@RestController
public class TransactionController {

    private final TransactionService transactionService;

    public  TransactionController(TransactionService transactionService){
        this.transactionService = transactionService;
    }

    @PostMapping("/transfer")
    public String transferMoney(@RequestParam Long fromAccountId,
                                @RequestParam Long toAccountId,
                                @RequestParam BigDecimal amount
                                ){
        transactionService.transferBetweenAccounts(fromAccountId,toAccountId,amount);
        return "Transfer successful";
    }

    @PostMapping("/play")
    public String makePayment(@RequestParam Long accountId,
                              @RequestParam BigDecimal amount,
                              @RequestParam String description
                              ){
        transactionService.makePayment(accountId,amount,description);
        return "Payment successful";
    }

    @GetMapping("/{accountId}/history")
    public List<Transaction> getTransactionHistory(@PathVariable Long accountId){
        return transactionService.getTransactionHistory(accountId);

    }
}