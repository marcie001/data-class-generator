package com.example.dataclassgenerator.singularizer;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SingularizerTests {

	@Autowired
	private Singularizer target;

	@Test
	public void testSingularize_IrregularPattern() {
		assertThat(target.singularize("trees"), is("tree"));
		assertThat(target.singularize("children"), is("child"));
	}

	@Test
	public void testSingularize_EndsWithIiPattern() {
		assertThat(target.singularize("octopii"), is("octopus"));
	}

	@Test
	public void testSingularize_EndsWithIesPattern() {
		assertThat(target.singularize("communities"), is("community"));
	}

	@Test
	public void testSingularize_EndsWithEsPattern() {
		assertThat(target.singularize("wishes"), is("wish"));
	}

	@Test
	public void testSingularize_EndsWithSPattern() {
		assertThat(target.singularize("products"), is("product"));
	}

}
