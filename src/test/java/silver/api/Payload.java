package silver.api;

public interface Payload {
    
    public String toJson();
    public void setValue(String fieldName, String fieldValue);
}
