# necessary libraries
require "net/http"
require "uri"
require "json"

# print statement asking user enter id
print "Enter Product ID "
# read the entered id
inputId = gets
inputId.chomp!
# uri to fetch product details by using productid and expanding the order details
uri = URI.parse("http://services.odata.org/Northwind/Northwind.svc/Products("+inputId+")?$expand=Order_Details&$format=json")
# make an http request using uri
response = Net::HTTP.get_response(uri)
# use json parsing to store the response body into data variable
data = JSON.parse(response.body)
productId = data['ProductID'].to_s
puts("Product ID: " + productId)
puts("")
# fetch all the orderdetails
orderDetails = data['Order_Details']
total = orderDetails.length
puts("The number of orders for product ID " + productId + " is " + total.to_s)
puts("Order ID's\tTotal Price after discount")
# iterate through value array and fetch value relevant to keys specified
orderDetails.each do |item|
	orderId = item["OrderID"].to_s
	price = item["UnitPrice"].to_i
	quantity = item["Quantity"].to_i
	discount = item["Discount"].to_f
	# calculate amount
	amount = price*quantity*(1-discount)
	# print orderId fetched and associated amount
	puts(orderId + "\t\t\t$" + amount.to_s)
end


