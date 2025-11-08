import java.util.*;

/**
 * There are n people, each person has a unique id between 0 and n-1. Given the arrays watchedVideos and friends, where
 * watchedVideos[i] and friends[i] contain the list of watched videos and the list of friends respectively for the
 * person with id = i.
 *
 * Level 1 of videos are all watched videos by your friends, level 2 of videos are all watched videos by the friends
 * of your friends and so on. In general, the level k of videos are all watched videos by people with the shortest
 * path exactly equal to k with you. Given your id and the level of videos, return the list of videos ordered by their
 * frequencies (increasing). For videos with the same frequency order them alphabetically from least to greatest.
 * */
public class GetWatchedVideosByYourFriends {
    // it is simple question, hume source node di hai , and hume voh node find krni hai jinka distance source node se "level"
    // ho. Fir jab vo node milegi toh us node ne jo videos watch kri hai uski frequency store krlo. esa har k dist door node
    // ke saath kro then sort the videos on frequency and return it
    public List<String> watchedVideosByFriends(List<List<String>> watchedVideos, int[][] friends, int id, int level) {
        HashMap<String,Integer> hashMap = new HashMap<>();

        Queue<FriendWithLevel> queue = new ArrayDeque<>();
        int visited[] = new int[friends.length];
        // start with friend with id = "id"
        queue.offer(new FriendWithLevel(id,0)); // initial level will be 0
        visited[id]=1;

        while (!queue.isEmpty()){
            FriendWithLevel friendWithLevel = queue.poll();
            int currentFriend = friendWithLevel.friend;
            int currentLevel = friendWithLevel.level;

            if(currentLevel==level){
                // get the movies of current friend
                List<String> videosWatchedByCurrentFriend = watchedVideos.get(currentFriend);
                for (String video: videosWatchedByCurrentFriend){
                    hashMap.put(video,hashMap.getOrDefault(video,0)+1);
                }
                // since current level agar target level hai toh iske neighbours target se jyaada ho jaaege toh unko visit mat kro
            }

            // since current level agar target level se bada hai toh iske neighbours target level se jyaada hi hoge toh unko visit mat kro
            if(currentLevel>level){continue;}

            // visit all neighbours if current level is less than target level
            for (int i = 0; i < friends[currentFriend].length; i++) {
                int neighbourFriend = friends[currentFriend][i];
                if(visited[neighbourFriend]==0){
                    queue.offer(new FriendWithLevel(neighbourFriend,currentLevel+1));
                    visited[neighbourFriend]=1;
                }
            }
        }

        System.out.println(hashMap);


        // now we have all videos of required level with their frequencies in hashmap
        List<VideoWithFrequency> tempAns = new ArrayList<>();

        for (Map.Entry<String,Integer> entry : hashMap.entrySet()){
            String video = entry.getKey();
            int frequency = entry.getValue();
            tempAns.add(new VideoWithFrequency(video,frequency));
        }

        System.out.println(tempAns);

        // now sort the temp ans on basis of frequency
        Collections.sort(tempAns);

        System.out.println(tempAns);

        List<String> ans = new ArrayList<>();

        for (int i = 0; i < tempAns.size(); i++) {
            ans.add(tempAns.get(i).video);
        }


        return ans;
    }

    class FriendWithLevel{
        int friend;
        int level;

        public FriendWithLevel(int friend, int level) {
            this.friend = friend;
            this.level = level;
        }
    }
    class VideoWithFrequency implements Comparable<VideoWithFrequency>{
        String video;
        int frequency;

        public VideoWithFrequency(String video, int frequency) {
            this.video = video;
            this.frequency = frequency;
        }

        @Override
        public int compareTo(VideoWithFrequency o) {
            if (this.frequency != o.frequency) {
                return this.frequency - o.frequency;
            } else {
                return this.video.compareTo(o.video);  // Sort lexicographically if frequencies match
            }
        }

    }
}
