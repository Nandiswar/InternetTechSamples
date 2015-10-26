# necessary libraries
require "net/http"
require "uri"
require "json"

uri = URI.parse("http://services.odata.org/Northwind/Northwind.svc/Order_Details?$select=OrderID,ProductID,Quantity,UnitPrice&$filter=ProductID eq 11&$format=json")
# make an http request using uri
response = Net::HTTP.get_response(uri)
# use json parsing to store the response body into data variable
#puts(response.body)
data = JSON.parse(response.body)

# fetch value array from json 
allValues = data['value']
# iterate through value array and print name of collection
sum = 0.to_i
allValues.each do |item|
	puts (item["UnitPrice"].to_f*item["Quantity"])
end
puts(sum.to_s)
#http://services.odata.org/Northwind/Northwind.svc/Order_Details?$select=OrderID&$filter=ProductID%20eq%202&$format=json
#http://services.odata.org/Northwind/Northwind.svc/Orders?$expand=Order_Details,Customer&$select=OrderID,Customer,Order_Details&$format=json