package com.giteat.ai.review.daemon.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MergeRequestId implements Serializable {
    private String repoId;
    private int prId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MergeRequestId that = (MergeRequestId) o;
        return prId == that.prId && // int는 == 으로 비교
                Objects.equals(repoId, that.repoId); // String은 Objects.equals 사용
    }

    @Override
    public int hashCode() {
        return Objects.hash(repoId, prId);
    }

}
