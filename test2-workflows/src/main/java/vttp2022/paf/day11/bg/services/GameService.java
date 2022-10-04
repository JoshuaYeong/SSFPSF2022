package vttp2022.paf.day11.bg.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp2022.paf.day11.bg.models.Game;
import vttp2022.paf.day11.bg.repositories.GameRepository;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepo;

    public Optional<Game> getComments(Integer gid) 
    {
        System.out.printf("GameService.getComments: %d\n", gid);
        
        Optional<Game> opt = gameRepo.getGameByGid(gid);

        if (opt.isEmpty())
            return Optional.empty();

        Game game = opt.get();
        game.setComments(gameRepo.getCommentsByGid(gid));
        return Optional.of(game);

    }


    
}
