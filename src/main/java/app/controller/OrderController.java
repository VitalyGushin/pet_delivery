package app.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.dao.OrderRepository;
import app.model.Orders;
import app.service.OrderService;

@Controller
public class OrderController {

	@Autowired
	private OrderRepository orderrepository;

	@Autowired
	private OrderService orderservice;

	private ArrayList<Orders> orderModelList;
	
	private ArrayList<Orders> orderModelList2;

	private List<String> undeliveredOrdersList = null;
	
	private List<String> postponedOrdersList = null;

	@GetMapping(value = "/")
	public String callcenterhome(Model model) {
		orderModelList = new ArrayList<Orders>();
		undeliveredOrdersList = orderservice.undeliveredOrders();
		for (String customer : undeliveredOrdersList) {
			Orders ord = orderrepository.findByCustomer(customer);
			orderModelList.add(ord);
		}
		model.addAttribute("search", orderModelList);
		
		orderModelList2 = new ArrayList<Orders>();
		postponedOrdersList = orderservice.postponedOrders();
		for (String customer : postponedOrdersList) {
			Orders ord = orderrepository.findByCustomer(customer);
			orderModelList2.add(ord);
		}
		model.addAttribute("search2", orderModelList2);
		
		model.addAttribute("orders", orderrepository.findAll());
		return "index";
	}

	@PostMapping(value = "/")
	public String addorder(@RequestParam("deliver") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date deliver,
						  @RequestParam("customer") String customer,
						  @RequestParam("address") String address,
						  @RequestParam("product") String product,
						  @RequestParam("phone") String phone) {
		orderservice.addOrder(deliver, customer, address, product, phone);
		return "redirect:/";
	}

	@PostMapping(value = "/delivere")
	public String delivereorder(@RequestParam("id") Long id) {
		orderservice.delivereOrder(id);
		return "redirect:/";
	}
	
	@PostMapping(value = "/postpone")
	public String postponeorder(@RequestParam("id") Long id) {
		orderservice.postponeOrder(id);
		return "redirect:/";
	}
	
	@PostMapping(value = "/change")
	public String changeorder(@RequestParam("id") Long id,
							  @RequestParam("deliver") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date deliver) {
		orderservice.changeOrder(id,deliver);
		return "redirect:/";
	}

}
