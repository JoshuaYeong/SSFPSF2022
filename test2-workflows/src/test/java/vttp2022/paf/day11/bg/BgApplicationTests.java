package vttp2022.paf.day11.bg;

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import vttp2022.paf.day11.bg.models.Comment;
import vttp2022.paf.day11.bg.models.Game;
import vttp2022.paf.day11.bg.repositories.GameRepository;
import vttp2022.paf.day11.bg.services.GameService;

@SpringBootTest
@AutoConfigureMockMvc
class BgApplicationTests {

	@Autowired
	private GameService gameSvc;

	// We are mocking gRepository
	@MockBean
	private GameRepository gRepository;

	// @Autowired
	// private GameRepository gameRepo;

	// @Test
	// void shouldReturnAGame() {

	// 	Optional<Game> opt = gameRepo.getGameByGid(10);
	// 	assertTrue(opt.isPresent(), "gid = 10");
	// }

	// @Test
	// void shouldReturnEmpty() {
	// 	Optional<Game> opt = gameRepo.getGameByGid(10000);
	// 	assertFalse(opt.isPresent(), "gid = 10000");
	// }

	// @Test
	// void shouldReturn42Comments() {

	// 	List<Comment> listOfComments = gameRepo.getCommentsByGid(10);
	// 	assertEquals(42, listOfComments.size(), "number of comments for gid = 10 is 42");
	// }

	@Test
	public void shouldReturn200() {
		int gid = 1;

		// For the first method
		Game game = new Game();
		game.setName("abc");

		// For the second method
		List<Comment> comments = new LinkedList<>();
		Comment c = new Comment();
		c.setGameId(gid);
		comments.add(c);

		Mockito.when(gRepository.getGameByGid(gid)).thenReturn(Optional.of(game));

		Mockito.when(gRepository.getCommentsByGid(gid)).thenReturn(comments);

		Optional<Game> opt = gameSvc.getComments(gid);

		assertTrue(opt.isPresent(), "Should found gid %d".formatted(gid));
	}

	@Test
	public void shouldReturnOptionalEmpty() {
		int game_id = -500;
		// The method to mock
		Mockito.when(gRepository.getGameByGid(game_id)).thenReturn(Optional.empty());

		// This will now use the mock
		Optional<Game> opt = gameSvc.getComments(game_id);

		assertFalse(opt.isPresent(), "Should not find gid %d".formatted(game_id));
	}

}
