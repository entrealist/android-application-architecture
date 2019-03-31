package com.rosberry.sample.surfaceviewrxed.presentation.main.myscene.ground;

import android.support.annotation.ColorInt;

import com.alexvasilkov.gestures.State;
import com.rosberry.sample.surfaceviewrxed.presentation.system.drawing.LayerState;

import org.jetbrains.annotations.NotNull;

/**
 * @author mmikhailov on 30/03/2019.
 */
public class GroundState extends State implements LayerState {

    @ColorInt
    private int backgroundColor;

    @Override
    public void update(@NotNull LayerState other) {
        if (other instanceof GroundState) {
            GroundState state = (GroundState) other;
            set(state);
        }
    }
}
