package com.adventofcode.day.five;

public abstract class JumpOpcode {

    private Integer instructionToJumpTo = null;

    public Integer jumpToInstruction() {
        return instructionToJumpTo;
    }

    protected void setInstructionToJumpTo(Integer instructionToJumpTo) {
        this.instructionToJumpTo = instructionToJumpTo;
    }

}
