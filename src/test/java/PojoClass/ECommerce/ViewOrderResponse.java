package PojoClass.ECommerce;

public class ViewOrderResponse {

	private OrderData_Response data;
	private String message;
	
	public OrderData_Response getData() {
		return data;
	}
	public void setData(OrderData_Response data) {
		this.data = data;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
