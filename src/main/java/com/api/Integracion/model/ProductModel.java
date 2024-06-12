package com.api.Integracion.model;

import com.api.Integracion.entity.Product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductModel {
	  private int id;
	  private  String name;
	  private  String description;
	  private BigDecimal price;
	  private Product category;
	  private String imageUrl;



}
