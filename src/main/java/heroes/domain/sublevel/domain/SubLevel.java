package heroes.domain.sublevel.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SubLevel {
    LV0101("예스 키즈존 등록하기"),
    LV0201("안전 기준 준수하기"),
    LV0202("기본 안전장치 설치하기"),
    LV0301("안전성 기준 충족하기"),
    LV0302("어린이 식기 구비하기"),
    LV0401("어린이 물티슈 제공하기"),
    LV0402("어린이용 의자 구비하기"),
    LV0501("아기를 위한 환경 마련하기"),
    LV0502("수유실 마련하기"),
    LV0601("부모 휴식 공간 마련하기"),
    LV0602("어린이 휴식 공간 마련하기"),
    LV0701("아이용 그리기 미술도구 구비하기"),
    LV0702("어린이 놀이방 만들기"),
    LV0801("유모차 비치공간 마련하기"),
    LV0802("가족 화장실 마련하기"),
    LV0901("양육환경 조성 직원교육 의무 실시하기"),
    LV1001("지역단체 협력 프로그램 조성하기"),
    LV1002("부모/아이 협력 프로그램 개설하기");

    private String value;
}
