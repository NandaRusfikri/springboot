package com.nanda.springboot.service;

import com.nanda.springboot.entity.User;
import com.nanda.springboot.model.CreateUserRequest;
import com.nanda.springboot.model.SearchUserRequest;
import com.nanda.springboot.model.UserResponse;
import com.nanda.springboot.repository.UserRepository;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private UserResponse toUserResponse(User user){
        return UserResponse.builder().
                email(user.getEmail()).name(user.getName()).password(user.getPassword()).build();
    }

    @Transactional
    public UserResponse create (CreateUserRequest request){

        User user = new User();
        user.setEmail(request.getEmail());
        user.setName(request.getName());
        user.setPassword(request.getPassword());

        userRepository.save(user);
        return toUserResponse(user);

    }

    @Transactional(readOnly = true)
    public Page<UserResponse> getList (User user, SearchUserRequest request){

        Specification<User> specification = (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
//            predicates.add(builder.equal(root.get("user"),user));

            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };

        Pageable pageable = PageRequest.of(1,1);
        Page<User> users = userRepository.findAll(specification,pageable);
        List<UserResponse> userResponses = users.getContent().stream().map(this::toUserResponse).toList();

        return new PageImpl<>(userResponses,pageable,users.getTotalElements());
    }
}
