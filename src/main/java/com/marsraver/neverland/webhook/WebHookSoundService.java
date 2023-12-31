package com.marsraver.neverland.webhook;

import com.marsraver.neverland.sound.Sound;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class WebHookSoundService {

    ExecutorService executorService = Executors.newFixedThreadPool(10);

    private RestTemplate restTemplate;

    private Map<String, Member> members = new HashMap<>();

    public WebHookSoundService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void playSound() {
        members.values().forEach(member -> executorService.submit(new SoundCall(member, member.getSound())));
    }

    public Member register(String hostName, String rootUrl, Sound sound) {
        return (hostName != null) ?
                members.put(hostName, new Member(hostName, rootUrl, sound))
                : null;

    }

    public Member unRegister(String hostName) {
        return members.remove(hostName);
    }

    public Collection<Member> getMembers() {
        return members.values();
    }

    @AllArgsConstructor
    private class SoundCall implements Callable<String> {

        private Member member;
        private Sound sound;

        @Override
        public String call() throws Exception {
            return restTemplate.getForEntity(member.rootUrl + "?sound=" + sound.name(), String.class)
                    .getBody();
        }
    }

    @Data
    @AllArgsConstructor
    public static final class Member {
        private String hostName;
        private String rootUrl;
        private Sound sound;
    }

}
