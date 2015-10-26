# necessary libraries
require "net/http"
require "uri"
require "json"

# print statement asking user enter id
print "Enter Product ID "
# read the entered id
inputId = gets
inputId.chomp!
# uri to fetch product details by using productid
uri = URI.parse("http://services.odata.org/Northwind/Northwind.svc/Products("+inputId+")?$format=json")
# make an http request using uri
response = Net::HTTP.get_response(uri)
# use json parsing to store the response body into data variable
data = JSON.parse(response.body)
# print the productid parsed from json 
puts("Product ID:" + data['ProductID'].to_s)
# print the productname parsed from json
puts("Product name:" + data['ProductName'].to_s)
# store the supplierid parsed from json
supplierId = data['SupplierID'].to_s
# print the supplierid
puts("Supplier ID:" + supplierId)
puts("Active/Discontinued")

# check if product is continued or not based on json parsed value
if(data['Discontinued'])
	puts(data['ProductName'].to_s + " is a discontinued product")
else
	puts(data['ProductName'].to_s + " is an active product")
end
# create a supplieruri using the supplier id fetched from previous call
supplierUri = URI.parse("http://services.odata.org/Northwind/Northwind.svc/Suppliers("+supplierId+")?$format=json")
# make an http request using uri
supplierResponse = Net::HTTP.get_response(supplierUri)
# use json parsing to store the response body into data variable
supplierData = JSON.parse(supplierResponse.body)
# print the supplier name parsed from json response
puts("Supplier name : " + supplierData['CompanyName'].to_s)
