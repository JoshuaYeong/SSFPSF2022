package vttp2022.day12.giphy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import vttp2022.day12.giphy.services.GiphyService;

@SpringBootTest
class GiphyApplicationTests {

	@Autowired
	private GiphyService giphySvc;

	@Test
	void shouldLoadTenImages() {
		List<String> gifs = giphySvc.getGifs("dog");
		assertEquals(10, gifs.size(), "Default number of gifs");
	}

}
