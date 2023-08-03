package project.Model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "insurance")
public class insurance {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "conclusion_date")
    private LocalDate conclusion_date;
    @Column(name = "company")
    private String company;
    @Column(name = "type")
    private String type;
    @Column(name = "begin_date")
    private LocalDate begin_date;
    @Column(name = "end_date")
    private LocalDate end_date;
    @Column(name = "fio")
    private String fio;
    @Column(name = "contract_number")
    private String contract_number;
    @Column(name = "vin")
    private String vin;
    @Column(name = "cost")
    private Integer cost;
    @Column(name = "percentage")
    private Integer percentage;
    @Column(name = "payments_number")
    private Integer payments_number;
    @Column(name = "kv1")
    private Integer kv1;
    @Column(name = "kv2")
    private Integer kv2;
    @Column(name = "signature_date1")
    private LocalDate signature_date1;
    @Column(name = "signature_date2")
    private LocalDate signature_date2;
    @Column(name = "payment_date1")
    private LocalDate payment_date1;
    @Column(name = "payment_date2")
    private LocalDate payment_date2;

    public insurance() {
    }

    public LocalDate getConclusion_date() {
        return conclusion_date;
    }

    public void setConclusion_date(LocalDate conclusion_date) {
        this.conclusion_date = conclusion_date;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getBegin_date() {
        return begin_date;
    }

    public void setBegin_date(LocalDate begin_date) {
        this.begin_date = begin_date;
    }

    public LocalDate getEnd_date() {
        return end_date;
    }

    public void setEnd_date(LocalDate end_date) {
        this.end_date = end_date;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getContract_number() {
        return contract_number;
    }

    public void setContract_number(String contract_number) {
        this.contract_number = contract_number;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public Integer getPercentage() {
        return percentage;
    }

    public void setPercentage(Integer percentage) {
        this.percentage = percentage;
    }

    public Integer getPayments_number() {
        return payments_number;
    }

    public void setPayments_number(Integer payments_number) {
        this.payments_number = payments_number;
    }

    public Integer getKv1() {
        return kv1;
    }

    public void setKv1(Integer kv1) {
        this.kv1 = kv1;
    }

    public Integer getKv2() {
        return kv2;
    }

    public void setKv2(Integer kv2) {
        this.kv2 = kv2;
    }

    public LocalDate getSignature_date1() {
        return signature_date1;
    }

    public void setSignature_date1(LocalDate signature_date1) {
        this.signature_date1 = signature_date1;
    }

    public LocalDate getSignature_date2() {
        return signature_date2;
    }

    public void setSignature_date2(LocalDate signature_date2) {
        this.signature_date2 = signature_date2;
    }

    public LocalDate getPayment_date1() {
        return payment_date1;
    }

    public void setPayment_date1(LocalDate payment_date1) {
        this.payment_date1 = payment_date1;
    }

    public LocalDate getPayment_date2() {
        return payment_date2;
    }

    public void setPayment_date2(LocalDate payment_date2) {
        this.payment_date2 = payment_date2;
    }

    @Override
    public String toString() {
        return String.format("%d %s %s %s %s %s %s %s %s %d %d %d %d %d %s %s %s %s",
                this.id, this.conclusion_date, this.company, this.type, this.begin_date, this.end_date, this.fio, this.contract_number,
                this.vin, this.cost, this.percentage, this.payments_number, this.kv1, this.kv2, this.signature_date1,
                this.signature_date2, this.payment_date1, this.payment_date2);
    }
}