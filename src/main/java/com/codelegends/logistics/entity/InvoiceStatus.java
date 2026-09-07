package com.codelegends.logistics.entity;

/**
 * Tracks whether an invoice still requires payment.
 */
public enum InvoiceStatus {
    // Indicates the generated invoice is still awaiting payment.
    UNPAID,
    PAID
}
