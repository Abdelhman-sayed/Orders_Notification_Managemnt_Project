# Orders_Notification_Management_Web_Service__Project

## Overview
The "Orders and Notifications Management" module is designed to facilitate the seamless handling of product orders and notifications within the system. This module incorporates various features to enhance the user experience, manage order placements, and provide detailed insights into notification activities.
<br><br>And This project was developed using Spring Boot.

## Customer Features
1) Product Information<br>
   Display a comprehensive list of all available products, including serial number, name, vendor, category, price, and remaining part count for each category.<br>
2) Customer Account Management<br>
   Allow customers to create accounts with a specified balance for future purchasing operations.<br>
3) Order Placement<br>
   Enable customers to place simple order.<br>
   Simple orders can include single or multiple products.<br>
   The system is configured to automatically generate notification for user who make placement order:<br>
   &nbsp;&nbsp;&nbsp;&nbsp; 1.Users will receive an immediate notification upon successfully placing an order.<br>
   &nbsp;&nbsp;&nbsp;&nbsp; 2.This notification will confirm the order details and acknowledge the successful placement.<br>
4) Order Shipping<br>
   Deduct shipping fees from the customer's account upon shipping for simple orders.<br>
   The system is configured to automatically generate notification for user who make shipment order:<br>
   &nbsp;&nbsp;&nbsp;&nbsp; 1.Upon the shipment of the order, users will receive a second notification.<br>
   &nbsp;&nbsp;&nbsp;&nbsp; 2.This notification will provide information about the shipped items, tracking details, and any relevant shipping updates.<br>
5) Order Cancellation<br>
   Enable customers to cancel order placements or cancel shipping within a pre-configured automated duration.<br>
## Admin Features
1) Order Details<br>
   Provide the ability to list all details of both simple and compound orders.<br>
2) Live Statistics<br>
   Provide live statistics on the overall software performance.<br>
   Identify the most notified email address/phone number.<br>
   Identify the most sent notification template.<br>
