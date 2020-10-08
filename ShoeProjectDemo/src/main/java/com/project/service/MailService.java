package com.project.service;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.project.dtos.OrderDetailDto;
import com.project.dtos.OrderDto;
import com.project.dtos.ProductReturnDto;
import com.project.repository.ProductRepository;

@Service
@Transactional
public class MailService {
	@Autowired
    public JavaMailSender javaMailSender;
	@Autowired
	private ProductRepository proService;

	public String sendEmail(OrderDto order) throws MessagingException, IOException {
		File htmlFile = new File("src\\main\\resources\\email.html");
		Document doc = Jsoup.parse(htmlFile, "UTF-8");
		
		Date toDay = new Date();
		double totalPayment = 0.0;
		String tableRow = "<tr class=\"row\">\r\n" + 
				"               <td class=\"col-md-3 col-lg-2 col-xl-2\">\r\n" + 
				"                 <div class=\"view zoom overlay z-depth-1 rounded mb-3 mb-md-0\">\r\n" + 
				"                   <img style=\"height = \"120px\"; width = \"130px\"; padding-right=\"5%;\"\" src=\"{{picture}}\"\" alt=\"Sample\">\r\n" + 
				"                 </div>\r\n" + 
				"               </td>\r\n" + 
				"               <td class=\"col-md-4\">\r\n" + 
				"                 <p><strong>Shoe:</strong> {{detail.name}}</p>\r\n" + 
				"                 <p><strong>Brand:</strong> {{detail.brand}}</p>\r\n" + 
				"                 <p><strong>Color:</strong> {{detail.color}}</p>\r\n" + 
				"                 <p><strong>Size:</strong> {{detail.size}}</p>\r\n" + 
				"               </td>\r\n" + 
				"               <td class=\"col-md-2 col-lg-2 col-xl-1\" style=\"text-align: center;\">\r\n" + 
				"                 <div style=\"margin-top: 60px;\" class=\"view zoom overlay z-depth-1 rounded mb-3 mb-md-0\">\r\n" + 
				"                   <h5>{{quantity}}</h5>\r\n" + 
				"                 </div>\r\n" + 
				"               </td>\r\n" + 
				"               <td class=\"col-md-2 col-lg-2 col-xl-2\" style=\"text-align: center;\">\r\n" + 
				"                 <div style=\"margin-top:60px;\" class=\"view zoom overlay z-depth-1 rounded mb-3 mb-md-0\">\r\n" + 
				"                   <h5>${{price*quantity}}</h5>\r\n" + 
				"                 </div>\r\n" + 
				"               </td>\r\n" + 
				"             </tr>";
		
		String newRows = "";
		
		for(OrderDetailDto detail: order.getListDetail()) {
			ProductReturnDto product = proService.getDetailByID(detail.getProductDetailID());

			totalPayment += detail.getQuantity()*product.getPrice();
			
			newRows += "\n" + tableRow.replace("{{detail.name}}", product.getName())
					.replace("{{picture}}", product.getPicture1())
					.replace("{{detail.brand}}", product.getBrand().toString())
					.replace("{{detail.color}}", product.getColor())
					.replace("{{detail.size}}", Integer.toString(product.getSize()))
					.replace("{{quantity}}", Integer.toString(detail.getQuantity()))
					.replace("{{price*quantity}}", Double.toString(product.getPrice()*detail.getQuantity())) + "\n";
		}
		
		String fullHtml = doc.html().replace("{{ordDate}}", toDay.toString())
				.replace("{{totalPayment}}", Double.toString(totalPayment))
				.replace("{{fullName}}", order.getFirstName() + " " + order.getLastName())
				.replace("{{address}}", order.getAddress())
				.replace("{{productDetail}}", newRows);

        MimeMessage msg = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        
        helper.setTo(order.getEmail());
        helper.setSubject("Testing from Spring Boot");
        helper.setText(fullHtml, true);

        javaMailSender.send(msg);
        return fullHtml;
    }
}
