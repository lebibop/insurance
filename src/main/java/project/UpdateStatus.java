package project;

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
}
