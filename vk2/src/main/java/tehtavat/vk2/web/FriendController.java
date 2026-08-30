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
public class FriendController {

    public static final List<Friend> friends = new ArrayList<>();

    static {
        friends.add(new Friend("Bob", "Smith"));
        friends.add(new Friend("David", "Parker"));
        friends.add(new Friend("Amanda", "Evans"));
    }

    @GetMapping("friends")
      public String listFriends(Model model) {
        model.addAttribute("friends", friends);
        return "friends";
    }

    @GetMapping("addfriend")
      public String showAddFriendForm(Model model) {
        model.addAttribute("friend", new Friend());
        return "newfriend";
    }

    @PostMapping("/addfriend")
        public String greetingSubmit(@ModelAttribute Friend friend, Model model) {
            friends.add(friend);
            return "redirect:/friends";
        }
}
