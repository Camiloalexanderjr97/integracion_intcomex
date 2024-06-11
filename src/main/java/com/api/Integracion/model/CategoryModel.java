package com.api.Integracion.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryModel {
	   private	int id;
	   private String name;
	   private String description;
	   public String imageUrl;
		
		
}
