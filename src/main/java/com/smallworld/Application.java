package com.smallworld;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smallworld.data.Transaction;

public class Application {

	public static void main(String[] args) {

		TransactionDataFetcher transactionDataFetcher = new TransactionDataFetcher();

		ObjectMapper objectMapper = new ObjectMapper();

		try {

			// Read the JSON file and map its content to the Person class
			List<Transaction> transactionsData = objectMapper.readValue(new File("transactions.json"),
					objectMapper.getTypeFactory().constructCollectionType(List.class, Transaction.class));

			System.out.println(transactionDataFetcher.getTotalTransactionAmount(transactionsData));
			System.out.println(transactionDataFetcher.getTotalTransactionAmountSentBy("Tom Shelby", transactionsData));
			System.out.println(transactionDataFetcher.getMaxTransactionAmount(transactionsData));
			System.out.println(transactionDataFetcher.countUniqueClients(transactionsData));
			System.out.println(transactionDataFetcher.hasOpenComplianceIssues("Tom Shelby", transactionsData));
			System.out.println(transactionDataFetcher.getTransactionsByBeneficiaryName(transactionsData));
			System.out.println(transactionDataFetcher.getUnsolvedIssueIds(transactionsData));
			System.out.println(transactionDataFetcher.getAllSolvedIssueMessages(transactionsData));
			System.out.println(transactionDataFetcher.getTop3TransactionsByAmount(transactionsData));
			System.out.println(transactionDataFetcher.getTopSender(transactionsData));

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
