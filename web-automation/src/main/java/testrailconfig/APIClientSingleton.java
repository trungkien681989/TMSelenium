package testrailconfig;

public class APIClientSingleton {

    private static APIClient apiClient;

    public static APIClient getInstance() {
        if (apiClient == null) {
            apiClient = new APIClient("https://hubcba.testrail.net/");
            apiClient.setUser("chung.pham@tyme.com");
            apiClient.setPassword("Unbreakable@1455");
        }
        return apiClient;
    }
}
