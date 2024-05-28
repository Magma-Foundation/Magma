package com.meti.compile;

import java.util.List;

public record ClassMemberResult(List<String> instanceMembers, List<String> staticMembers) {
}
