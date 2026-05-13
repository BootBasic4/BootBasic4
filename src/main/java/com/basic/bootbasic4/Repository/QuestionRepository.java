package com.basic.bootbasic4.Repository;

import com.basic.bootbasic4.entity.Question;
import com.basic.bootbasic4.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
