# necessary libraries
require "net/http"
require "uri"
require "json"

# print statement asking user to enter product id
print "Enter Product ID and I will find Customer's who ordered it "
# read the entered value
inputId = gets
inputId.chomp!
# print the entered value
puts("Product ID: " + inputId)
puts()
# uri to find order id's associated with given procut id
uri = URI.parse("http://services.odata.org/Northwind/Northwind.svc/Order_Details?$select=OrderID&$filter=ProductID eq "+inputId+"&$format=json")
# make an http request using uri
response = Net::HTTP.get_response(uri)
# use json parsing to store the response body into data variable
data = JSON.parse(response.body)
orderDetails = data['value']
total = orderDetails.length
puts("The number of orders for product ID " + inputId + " is " + total.to_s)
orders_filter = ""
# creating a filter criteria to check orderid's
orderDetails.each do |item|
	orders_filter += ("OrderID eq " + item["OrderID"].to_s + " or ")
end
length = orders_filter.length
# removing the end extra or appended
query_param = orders_filter[0,length-4]
# show order and customer details only those fit in the filter criteria (orderid's) specified
customers_uri = URI.parse("http://services.odata.org/Northwind/Northwind.svc/Orders?$expand=Customer&$filter="+query_param+"&$select=OrderID,Customer&$format=json")
# make an http request using uri
#puts(customers_uri)
customers_response = Net::HTTP.get_response(customers_uri)
# use json parsing to store the response body into customers_data variable
customers_data = JSON.parse(customers_response.body)
customers = customers_data['value']
#puts(customers.to_s)
# iterate through customers array, get the Customer object and then print name of contact
customers.each do |customer|
	customer_details = customer["Customer"]
	customer_name = customer_details["ContactName"]
	puts(customer_name.to_s)
end

