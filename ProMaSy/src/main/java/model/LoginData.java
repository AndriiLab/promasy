package model;

public final class LoginData {

    private static volatile EmployeeModel instance;

    public static EmployeeModel getInstance(EmployeeModel model) {
        EmployeeModel localInstance = instance;
        if (localInstance == null) {
            synchronized (EmployeeModel.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = model;
                }
            }
        }
        return localInstance;
    }

    public static EmployeeModel getInstance() {
        return instance;
    }
}
