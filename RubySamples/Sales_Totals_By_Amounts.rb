# necessary libraries
require "net/http"
require "uri"
require "json"

uri = URI.parse("http://services.odata.org/Northwind/Northwind.svc/Sales_Totals_by_Amounts/?$format=json")
# make an http request using uri
response = Net::HTTP.get_response(uri)
# use json parsing to store the response body into data variable
data = JSON.parse(response.body)
# fetch value array from json 
allValues = data['value']
# iterate through value array and print name of collection
allValues.each do |item|
	puts item["SaleAmount"]
end
