package com.smallworld;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.smallworld.data.Transaction;

public class TransactionDataFetcher {

	/**
	 * Returns the sum of the amounts of all transactions
	 */
	public double getTotalTransactionAmount(List<Transaction> transaction) {

		double totalTransactionAmount = 0;
		for (Transaction transData : transaction) {
			totalTransactionAmount = totalTransactionAmount + transData.getAmount();
		}
		return totalTransactionAmount;
	}

	/**
	 * Returns the sum of the amounts of all transactions sent by the specified
	 * client
	 */
	public double getTotalTransactionAmountSentBy(String senderFullName, List<Transaction> transaction) {

		double totalTransactionAmountByClient = 0;
		for (Transaction transData : transaction) {
			if (senderFullName.equals(transData.getSenderFullName())) {
				totalTransactionAmountByClient = totalTransactionAmountByClient + transData.getAmount();
			}
		}
		return totalTransactionAmountByClient;
	}

	/**
	 * Returns the highest transaction amount
	 */
	public double getMaxTransactionAmount(List<Transaction> transaction) {
		double maxTransactionAmount = 0;
		for (Transaction transData : transaction) {
			if (transData.getAmount() > maxTransactionAmount) {
				maxTransactionAmount = transData.getAmount();
			}
		}
		return maxTransactionAmount;
	}

	/**
	 * Counts the number of unique clients that sent or received a transaction
	 */
	public long countUniqueClients(List<Transaction> transaction) {

		Set<String> uniqueClients = new HashSet<>();

		for (Transaction transData : transaction) {
			uniqueClients.add(transData.getSenderFullName());
			uniqueClients.add(transData.getBeneficiaryFullName());
		}

		return uniqueClients.size();
	}

	/**
	 * Returns whether a client (sender or beneficiary) has at least one transaction
	 * with a compliance issue that has not been solved
	 */
	public boolean hasOpenComplianceIssues(String clientFullName, List<Transaction> transaction) {
		boolean response = false;
		for (Transaction transData : transaction) {
			if ((transData.getSenderFullName().equals(clientFullName)
					|| transData.getBeneficiaryFullName().equals(clientFullName))
					&& Boolean.FALSE.equals(transData.isIssueSolved())) {
				response = true;
			}
		}
		return response;
	}

	/**
	 * Returns all transactions indexed by beneficiary name
	 */
	public Map<String, Transaction> getTransactionsByBeneficiaryName(List<Transaction> transactions) {
		Map<String, Transaction> beneficiaryMap = new HashMap<>();

		for (Transaction transaction : transactions) {
			beneficiaryMap.put(transaction.getBeneficiaryFullName(), transaction);
		}

		return beneficiaryMap;
	}

	/**
	 * Returns the identifiers of all open compliance issues
	 */
	public Set<Integer> getUnsolvedIssueIds(List<Transaction> transaction) {
		return transaction.stream().filter(tx -> !tx.isIssueSolved()).map(Transaction::getIssueId)
				.collect(Collectors.toSet());
	}

	/**
	 * Returns a list of all solved issue messages
	 */
	public List<String> getAllSolvedIssueMessages(List<Transaction> transaction) {

		List<String> solvedIssueMessages = new ArrayList<>();
		for (Transaction transData : transaction) {
			if ((Boolean.TRUE.equals(transData.isIssueSolved()) && transData.getIssueMessage() != null)) {
				solvedIssueMessages.add(transData.getIssueMessage());
			}
		}
		return solvedIssueMessages;
	}

	/**
	 * Returns the 3 transactions with the highest amount sorted by amount
	 * descending
	 */
	public List<Transaction> getTop3TransactionsByAmount(List<Transaction> transaction) {
		return transaction.stream().sorted(Comparator.comparingDouble(Transaction::getAmount).reversed()).limit(3)
				.collect(Collectors.toList());
	}

	/**
	 * Returns the senderFullName of the sender with the most total sent amount
	 */
	public Optional<String> getTopSender(List<Transaction> transaction) {
		Map<String, Double> senderTotalAmounts = new HashMap<>();

		for (Transaction tx : transaction) {
			senderTotalAmounts.put(tx.getSenderFullName(),
					senderTotalAmounts.getOrDefault(tx.getSenderFullName(), 0.0) + tx.getAmount());
		}

		return senderTotalAmounts.entrySet().stream().max(Comparator.comparingDouble(Map.Entry::getValue))
				.map(Map.Entry::getKey);
	}

}
