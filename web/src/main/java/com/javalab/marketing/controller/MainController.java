package com.javalab.marketing.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import com.javalab.marketing.models.*;
import com.javalab.marketing.dao.OrderManager;
import com.javalab.marketing.dao.SupportManager;
import com.javalab.marketing.dao.UserManager;
import com.javalab.marketing.services.Sender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Handles requests for the application home page.
 */
@Controller
public class MainController {

	private static Logger log = Logger.getLogger(MainController.class.getName());

	//A small website

	/*
	Return lending
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model) {
		return "index";
	}

	//Monitoring URL for Ios Devices
	@RequestMapping(value = "/monitoringios",method = RequestMethod.POST)
	@ResponseBody
	public void monitoringURL(@RequestBody InfoIOS info,Model model) throws SQLException, ClassNotFoundException {
		String appName = info.getApp_name();
		OrderManager orderManager = new OrderManager();
		orderManager.upEarn(appName);
	}

	//Accept purchase
	@RequestMapping(value = "/acceptpurchase")
	public void acceptPurchase(@RequestParam int operation_id,@RequestParam String notification_type,@RequestParam String datetime,@RequestParam String sha1_hash, @RequestParam String sender ,@RequestParam boolean codepro, @RequestParam int currency ,@RequestParam double amount,@RequestParam double withdraw_amount,@RequestParam String label,Model model) throws SQLException, ClassNotFoundException {
		OrderManager orderManager = new OrderManager();
		int idorder = Integer.parseInt(label.substring(17, label.length()));
		Order order = orderManager.getOrder(idorder);
		if(order.getPrice()==withdraw_amount){
			orderManager.payment(idorder);
		}else {
			orderManager.updatePayment(idorder,order.getPrice()-withdraw_amount);
		}
	}

	//Monitoring URL for Android Devices
	@RequestMapping(value = "/monitoringandroid",method = RequestMethod.POST)
	@ResponseBody
	public void monitoringURL(@RequestBody InfoAndroid info,Model model) throws SQLException, ClassNotFoundException {
		String appName = info.getApp_name();
		OrderManager orderManager = new OrderManager();
		orderManager.upEarn(appName);
	}


	//Admin area

	/*
	Show main admin page;
	 */
	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String adminStart(Model model) {
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		log.info("Open admin page by "+user.getUsername());
		model.addAttribute("user_login",user.getUsername());
		return "admin";
	}

	/*
	This method shows all users in admin panel
	 */
	@RequestMapping(value = "/admin/users", method = RequestMethod.GET)
	public String showAllUsers(Model model) throws SQLException, ClassNotFoundException {
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		log.info("Show users by "+user.getUsername());
		model.addAttribute("user_login",user.getUsername());
		UserManager userManager = new UserManager();
		List<User> users = userManager.getAll();
		OrderManager orderManager = new OrderManager();
		model.addAttribute("users",users);
		return "admin/users";
	}

	/*
	This method show all orders from users in admin panel
	 */
	@RequestMapping(value = "/admin/orders", method = RequestMethod.GET)
	public String adminOrdersView(Model model) throws SQLException, ClassNotFoundException {
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		log.info("Show all orders by "+user.getUsername());
		model.addAttribute("user_login",user.getUsername());
		OrderManager orderManager = new OrderManager();
		List<Order> orders = orderManager.getAll();
		model.addAttribute("orders",orders);
		return "admin/orders";
	}

	/*
	Show view for accept order, call by click on order
	 */
	@RequestMapping(value = "/admin/orders/acceptorder", method = RequestMethod.GET)
	public String acceptOrderView(@RequestParam int id,Model model) throws SQLException, ClassNotFoundException {
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("user_login", user.getUsername());
		OrderManager orderManager = new OrderManager();
		Order order = orderManager.getOrder(id);
		if(order.getState().equals("В обработке")){
			model.addAttribute("accept","что заказ готов к оплате");
		}else if(order.getState().equals("Требуется оплата")){
			model.addAttribute("accept","что оплата получена");
		}else if(order.getState().equals("Выполняется")){
			model.addAttribute("accept","что заказ выполнен");
		}
		log.info("Accept order with id "+order.getId()+" by "+user.getUsername());
		model.addAttribute("order",order);
		return "admin/acceptorder";
	}

	/*
	This method transfer order from wait to need pay
	 */
	@RequestMapping(value = "/admin/orders/acceptorder", method = RequestMethod.POST)
	public String acceptOrder(@RequestParam int id,Model model) throws SQLException, ClassNotFoundException {
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("user_login",user.getUsername());
		OrderManager orderManager = new OrderManager();
		Order order = orderManager.getOrder(id);
		if(order.getState().equals("В обработке")) {
			orderManager.acceptOrder(id);
		}else if(order.getState().equals("Требуется оплата")){
			orderManager.payment(id);
		}else if (order.getState().equals("Выполняется")){
			orderManager.completeOrder(id);
			Sender sender = new Sender("thefp0zt@gmail.com", "882501qQ");
			sender.send("Marketing Group", "Order with ID "+order.getId()+" and Appname "+order.getAppname()+" is complete", user.getEmail());
		}
		log.info("Accept order with id "+id+" by "+user.getUsername()+" complete");
		return "redirect:/admin/orders";
	}

	/*
	This method for delete order (only admin)
	 */
	@RequestMapping(value = "/admin/orders/delete", method = RequestMethod.GET)
	public String deleteOrder(@RequestParam int id,Model model) throws SQLException, ClassNotFoundException {
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("user_login",user.getUsername());
		OrderManager orderManager = new OrderManager();
		orderManager.deleteOrder(id);
		log.info("Delete order with id "+id+" by "+user.getUsername()+" complete");
		return "redirect:/admin/orders";
	}

	/*
	This method show all supports(questions from users)
	 */
	@RequestMapping(value = "/admin/supports", method = RequestMethod.GET)
	public String adminSupportView(Model model) throws SQLException, ClassNotFoundException {
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("user_login",user.getUsername());
		SupportManager supportManager = new SupportManager();
		List<Support> supports = supportManager.getAll();
		model.addAttribute("supports",supports);
		log.info("Show all supports by "+user.getUsername());
		return "admin/supports";
	}

	/*
	This method show one support(question to admin) and its details
	 */
	@RequestMapping(value = "/admin/supports/answer", method = RequestMethod.GET)
	public String adminSupportAnswerView(@RequestParam int id,Model model) throws SQLException, ClassNotFoundException {
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("user_login",user.getUsername());
		SupportManager supportManager = new SupportManager();
		Support support = supportManager.getBySupportId(id);
		model.addAttribute("support",support);
		log.info("Open support with id "+id+" by "+user.getUsername()+" complete");
		return "admin/support";
	}

	/*
	This method for answer to support(question to admin)
	 */
	@RequestMapping(value = "/admin/supports/answer", method = RequestMethod.POST)
	public String adminSupportAnswer(@RequestParam int id,@RequestParam String messageadmin,Model model) throws SQLException, ClassNotFoundException {
		SupportManager supportManager = new SupportManager();
		supportManager.answerSupport(id,messageadmin);
		log.info("Answer to support with id "+id+" complete");
		return "redirect:/admin/supports";
	}


	//Personal Area

	/*
	This method show page user account menu
	 */
	@RequestMapping(value = "/secure", method = RequestMethod.GET)
	public String personalPage(Model model) {
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("user_login",user.getUsername());
		return "secure";
	}

	/*
	This method show all orders by user in user account menu
	 */
	@RequestMapping(value = "/secure/orders", method = RequestMethod.GET)
	public String ordersView(Model model) throws SQLException, ClassNotFoundException {
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("user_login",user.getUsername());
		OrderManager orderManager = new OrderManager();
		List<Order> orders = orderManager.getByUserId(user.getId());
		model.addAttribute("orders",orders);
		return "secure/orders";
	}

	/*
	This method show page, which add order (User add order)
	 */
	@RequestMapping(value = "/secure/orders/add", method = RequestMethod.GET)
	public String addOrdersView(Model model) {
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("user_login",user.getUsername());
		return "secure/addorder";
	}

	/*
	This method add order to database
	 */
	@RequestMapping(value = "/secure/orders/add", method = RequestMethod.POST)
	public String addOrder(@RequestParam String appname,@RequestParam String hrefappstore,@RequestParam String hrefplaymarket,@RequestParam String service,@RequestParam int count,Model model) throws SQLException, ClassNotFoundException {
		OrderManager orderManager = new OrderManager();
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		double price=0;
		if(service.equals("clicks")){
			price=1.5;
			price=price*count;
			Order order = new Order(user.getId(),appname,hrefappstore,hrefplaymarket,service,count,price,"В обработке");
			orderManager.createOrder(order);
		} else if(service.equals("installs")){
			price=20;
			price=price*count;
			Order order = new Order(user.getId(),appname,hrefappstore,hrefplaymarket,service,count,price,"В обработке");
			orderManager.createOrder(order);
		}
		log.info("Order with appname "+appname+" add to database by "+user.getUsername());
		return "redirect:/secure/orders";
	}

	/*
	This method show monitoring page with user's orders and their statuses
	 */
	@RequestMapping(value = "/secure/monitoring", method = RequestMethod.GET)
	public String monitoringView(Model model) throws SQLException, ClassNotFoundException {
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("user_login",user.getUsername());
		OrderManager orderManager = new OrderManager();
		List<Order> orders = orderManager.getByUserId(user.getId());
		model.addAttribute("orders",orders);
		return "secure/monitoring";
	}

	/*
	This method initial payment in user account menu
	 */
	@RequestMapping(value = "/secure/pay", method = RequestMethod.GET)
	public String payment(@RequestParam int id,Model model) throws SQLException, ClassNotFoundException {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("user_login", user.getUsername());
		OrderManager orderManager = new OrderManager();
		Order order = orderManager.getOrder(id);
		model.addAttribute("price",order.getPrice());
		model.addAttribute("idorder",order.getId());
		log.info("Order with id "+id+" paid by "+user.getUsername());
		return "secure/pay";
	}

	/*
	This method show all orders which user can pay
	 */
	@RequestMapping(value = "/secure/payment", method = RequestMethod.GET)
	public String paymentView(Model model) throws SQLException, ClassNotFoundException {
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("user_login",user.getUsername());
		OrderManager orderManager = new OrderManager();
		List<Order> orders = orderManager.getByUserId(user.getId());
		model.addAttribute("orders",orders);
		return "secure/payment";
	}

	/*
	This method show all supports(questions to admin) which user sent
	 */
	@RequestMapping(value = "/secure/support", method = RequestMethod.GET)
	public String supportView(Model model) throws SQLException, ClassNotFoundException {
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("user_login",user.getUsername());
		SupportManager supportManager = new SupportManager();
		List<Support> supports = supportManager.getAllById(user.getId());
		model.addAttribute("supports",supports);
		return "secure/support";
	}

	/*
	This method show create support page
	 */
	@RequestMapping(value = "/secure/support/add", method = RequestMethod.GET)
	public String supportAddView(Model model) {
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("user_login",user.getUsername());
		return "secure/addsupport";
	}

	/*
	This method create and sent support to database
	 */
	@RequestMapping(value = "/secure/support/add", method = RequestMethod.POST)
	public String supportAdd(@RequestParam String theme,@RequestParam String messageuser,Model model) throws SQLException, ClassNotFoundException {
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("user_login",user.getUsername());
		SupportManager supportManager = new SupportManager();
		supportManager.createSupport(user.getId(),theme,messageuser);
		log.info("User with username "+user.getUsername()+" sent support with theme "+theme);
		return "redirect:/secure/support";
	}

	/*
	This method show one support by click
	 */
	@RequestMapping(value = "/secure/support/show", method = RequestMethod.GET)
	public String supportShow(@RequestParam int id, Model model) throws SQLException, ClassNotFoundException {
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("user_login",user.getUsername());
		SupportManager supportManager = new SupportManager();
		Support support = supportManager.getBySupportId(id);
		model.addAttribute("support",support);
		return "secure/showsupport";
	}

	//Authorization user

	/*
	This URL for logout, configuration file /WEB-INF/spring/application-security.xml
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logoutUser(Model model) {
		return "user/logout";
	}

	/*
	This URL for login, configuration file /WEB-INF/spring/application-security.xml
	 */
	@RequestMapping(value = "/signin", method = RequestMethod.GET)
	public String signinView(Model model) {
		return "user/signin";
	}

	/*
	Show, if you write wrong password or username, configuration file /WEB-INF/spring/application-security.xml
	 */
	@RequestMapping(value = "/signin-error", method = RequestMethod.GET)
	 public String signinError(Model model) {
		model.addAttribute("error","Логин или пароль не верны");
		return "user/signin";
	}

	/*
	This URL for registration new users
	And it return registration page
	 */
	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public String registrationView(Model model) {
		return "user/registration";
	}

	/*
	This method sent data from registration page to data base and create new user
	 */
	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public String registrationUser(@RequestParam String email, @RequestParam String username,@RequestParam String password, Model model) throws SQLException, ClassNotFoundException {
		UserManager userManager = new UserManager();
		if(userManager.busyUsername(username)) {
			userManager.create(email, username, password, "ROLE_USER");
			log.info("Create new user with username "+username);
			return "user/signin";
		}else {
			model.addAttribute("error","Этот никнейм уже занят");
			return "user/registration";
		}
	}
	
}
