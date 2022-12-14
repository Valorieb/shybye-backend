package com.GDI_Hackathon.ShyBye.Controllers;

import com.GDI_Hackathon.ShyBye.Daos.ChallengeDao;
import com.GDI_Hackathon.ShyBye.Daos.RewardDao;
import com.GDI_Hackathon.ShyBye.Daos.UserDao;
import com.GDI_Hackathon.ShyBye.Models.Challenge;
import com.GDI_Hackathon.ShyBye.Models.CompleteChallenge;
import com.GDI_Hackathon.ShyBye.Models.Reward;
import com.GDI_Hackathon.ShyBye.Models.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class ChallengeController {

    private ChallengeDao challengeDao;
    private UserDao userDao;
    private RewardDao rewardDao;

    public ChallengeController(ChallengeDao challengeDao, UserDao userDao, RewardDao rewardDao) {
        this.challengeDao = challengeDao;
        this.userDao = userDao;
        this.rewardDao = rewardDao;
    }

    //Submit completed challenge to userId
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/users/{userId}/challenges/{challengeId}", method = RequestMethod.POST)
    public User submitCompletedChallenge(@PathVariable int userId, @PathVariable int challengeId){
        challengeDao.submitCompletedChallenge(userId, challengeId);
        User user = userDao.getUserById(userId);
        int points = challengeDao.getChallengeById(challengeId).getChallengePoints();
        user.addToScore(points);
        return userDao.updateUserScore(user);
    }

    //Completed Challenge History by userId
    @ResponseStatus(HttpStatus.FOUND)
    @RequestMapping(path = "/users/{userId}/challenges", method = RequestMethod.GET)
    public List<CompleteChallenge> getUserChallengeHistory(@PathVariable int userId){
        return challengeDao.getUserHistory(userId);
    }

    //Get list of challenges
    @ResponseStatus(HttpStatus.FOUND)
    @RequestMapping(path = "/challenges", method = RequestMethod.GET)
    public List<Challenge> getListOfChallenges(){
        return challengeDao.getListOfChallenges();
    }

    //Get list of rewards
    @ResponseStatus(HttpStatus.FOUND)
    @RequestMapping(path = "/rewards", method = RequestMethod.GET)
    public List<Reward> getListOfRewards(){
        return rewardDao.getListOfRewards();
    }


}
