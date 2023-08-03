package project.Helpers;

import project.Model.insurance;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Класс для проверки на добавление элемента в базу данных
 * @author lebibop
 */
public final class UpdateStatus {

    private UpdateStatus() {}

    private static boolean isInsuranceAdded;

    public static boolean isIsInsuranceAdded() {
        return isInsuranceAdded;
    }

    public static void setIsInsuranceAdded(boolean isInsuranceAdded) {
        UpdateStatus.isInsuranceAdded = isInsuranceAdded;
    }

    public static void save(List<insurance> List) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("database/saves.csv"));

        for(insurance workers : List)
        {
            writer.write(workers.getConclusion_date() + ";" + workers.getCompany() + ";"
                    + workers.getType() + ";" + workers.getBegin_date() + ";" + workers.getEnd_date() + ";"
                    + workers.getFio() + ";" + workers.getContract_number() + ";" + workers.getVin() + ";"
                    + workers.getCost() + ";" + workers.getPercentage() + ";" + workers.getPayments_number() + ";"
                    + workers.getKv1() + ";" + workers.getKv2() + ";" + workers.getSignature_date1() + ";"
                    + workers.getSignature_date2() + ";" + workers.getPayment_date1() + ";" +
                    workers.getPayment_date2());
            writer.newLine();
        }
        writer.close();
    }
}
