package org.jsondoc.sample.data;

import java.util.List;
import java.util.Random;

import org.jsondoc.sample.pojo.City;
import org.jsondoc.sample.pojo.Continent;
import org.jsondoc.sample.pojo.Country;

import com.google.common.collect.Lists;

public class SampleData {

	private static Random population = new Random(5000);
	private static Random squareKm = new Random(20);

	public static City melbourne = new City("Melbourne", population.nextInt(), squareKm.nextInt());
	public static City sydney = new City("Sydney", population.nextInt(), squareKm.nextInt());
	public static City perth = new City("Perth", population.nextInt(), squareKm.nextInt());
	public static Country australia = new Country(population.nextInt(), squareKm.nextInt(), "Australia", Lists.newArrayList(melbourne, sydney, perth), Continent.AUSTRALIA);

	public static City newyork = new City("Ney York", population.nextInt(), squareKm.nextInt());
	public static City boston = new City("Boston", population.nextInt(), squareKm.nextInt());
	public static City sanfrancisco = new City("San Francisco", population.nextInt(), squareKm.nextInt());
	public static Country us = new Country(population.nextInt(), squareKm.nextInt(), "U.S.A.", Lists.newArrayList(newyork, boston, sanfrancisco), Continent.AMERICA);
	
	public static List<Country> countries = Lists.newArrayList(australia, us);

}
