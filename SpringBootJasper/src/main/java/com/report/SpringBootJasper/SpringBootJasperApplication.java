package com.report.SpringBootJasper;

import net.sf.jasperreports.engine.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class SpringBootJasperApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootJasperApplication.class, args);
	}

	//para que funcione se crea un nuevo parametro en el xml para el directorio de las imagenes.

	@Bean
	CommandLineRunner init(){
		return args -> {
			String destinationPath = "src" + File.separator + "main" + File.separator + "resources" + File.separator + "static" + File.separator + "ReportGenerated.pdf";

			String filePath = "src" + File.separator + "main" + File.separator + "resources" + File.separator + "templates" + File.separator + "report" + File.separator + "Report.jrxml";

			LocalDateTime fecha = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

			Map<String, Object> parameters = new HashMap<>();
			parameters.put("voucher_id", "0000045634");
			parameters.put("fecha", formatter.format(fecha));
			parameters.put("AmoutPaid", new BigDecimal(30000));
			parameters.put("payment_method", "cach");
			parameters.put("parent_name", "lkajsdlka kas");
			parameters.put("child_name", "ajksdasu");
			parameters.put("imageDir", "classpath:/static/images/"); 													//donde estan las imagenes para generar el archivo.

			JasperReport report = JasperCompileManager.compileReport(filePath);											//con esto se compila el archivo xml Report.jrxml y se convierte en un archivo de tipo jasper report.
			JasperPrint print = JasperFillManager.fillReport(report, parameters, new JREmptyDataSource());				//con esto llenas el reporte. Se usa new JREmptyDataSource() pq no se está usando base de datos.
			JasperExportManager.exportReportToPdfFile(print, destinationPath);
			System.out.println("Report created successfully!");


		};
	}
}
