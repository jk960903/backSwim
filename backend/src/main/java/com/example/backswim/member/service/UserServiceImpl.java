package com.example.backswim.member.service;

import com.example.backswim.component.MailComponents;
import com.example.backswim.member.entity.EmailEntity;
import com.example.backswim.member.entity.UserEntity;
import com.example.backswim.member.params.CheckDuplicateID;
import com.example.backswim.member.params.JoinMemberParam;
import com.example.backswim.member.repository.EmailRepository;
import com.example.backswim.member.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    private final MailComponents mailComponents;

    private final EmailRepository emailRepository;
    @Override
    public boolean joinMember(JoinMemberParam param) {
        Optional<UserEntity> optionalUser = userRepository.findByUserEmail(param.getUserEmail());

        if(optionalUser.isPresent()){
            return false;
        }

        String encPassword = BCrypt.hashpw(param.getPassword(),BCrypt.gensalt());

        String uuid = UUID.randomUUID().toString();

        UserEntity userEntity = UserEntity.builder()
                .userEmail(param.getUserEmail())
                .emailAuthYn(false)
                .password(encPassword)
                .emailAuthKey(uuid)
                .regdate(LocalDateTime.now())
                .emailAuthDate(LocalDateTime.now())
                .build();

        userRepository.save(userEntity);

        UserEntity tempEntity = userRepository.findByUserEmail(param.getUserEmail()).get();

        EmailEntity emailEntity = EmailEntity.builder()
                .EmailTitle("BackSwim 가입 인증 이메일")
                .userEmail(tempEntity.getUserEmail())
                .userId(userEntity.getSeq())
                .EmailContent("<p>"+" BackSwim 사이트 가입을 축하드립니다.</p>\n<p>아래 링크를 클릭하셔서 가입을 완료하세요.</p>\n"
                        + "<div><a target='_blank' href='http://localhost:8080/api/joinmember/email-auth?uuid="+uuid+"'>가입완료</a></div>").build();

        emailRepository.save(emailEntity);

        mailComponents.sendMail(emailEntity.getSeq());

        return true;
    }

    @Override
    public boolean duplicateEmail(CheckDuplicateID param) {

        Optional<UserEntity> optionalUser = userRepository.findByUserEmail(param.getUserEmail());

        if(optionalUser.isPresent()){
            return false;
        }

        return true;
    }

    @Override
    public boolean emailAuth(String uuid) {
        Optional<UserEntity> optionalUser = userRepository.findByEmailAuthKey(uuid);

        if(optionalUser.isEmpty()){
            return false;
        }
        UserEntity userEntity = optionalUser.get();

        userEntity.setEmailAuthYn(true);
        userEntity.setEmailAuthKey(null);
        userEntity.setEmailAuthDate(LocalDateTime.now());

        userRepository.save(userEntity);

        return true;
    }


}
