package spring.project.configuration;

enum PaymentStatus {
    PAID,
    UNPAID,
    REFUNDED
}

class PaymentStatusStateMachine {
    private PaymentStatus currentStatus;

    public PaymentStatusStateMachine(PaymentStatus initialStatus) {
        this.currentStatus = initialStatus;
    }

    public PaymentStatus getCurrentStatus() {
        return currentStatus;
    }

    public void pay() {
        currentStatus = PaymentStatus.PAID;
    }

    public void refund() {
        currentStatus = PaymentStatus.REFUNDED;
    }
    public String getPaymentStatus(String paymentStatus) {
        PaymentStatus paymentStatusEnum = PaymentStatus.valueOf(paymentStatus);
        PaymentStatusStateMachine stateMachine = new PaymentStatusStateMachine(paymentStatusEnum);
        return stateMachine.getCurrentStatus().name();
    }
}