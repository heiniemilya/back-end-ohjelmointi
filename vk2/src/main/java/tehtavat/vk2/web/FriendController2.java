package tehtavat.vk2.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import tehtavat.vk2.domain.Friend;

@Controller
public class FriendController2 {

    public static final List<Friend> friends = new ArrayList<>();

    static {
        friends.add(new Friend("Bob", "Smith"));
        friends.add(new Friend("David", "Parker"));
        friends.add(new Friend("Amanda", "Evans"));
    }

    @GetMapping("friends2")
      public String listFriends(Model model) {
        model.addAttribute("friends", friends);
        model.addAttribute("friend", new Friend());
        return "friends2";
    }

    @PostMapping("friends2")
        public String addFriend(@ModelAttribute Friend friend) {
            friends.add(friend);
        return "redirect:/friends2";
    }
        
}
