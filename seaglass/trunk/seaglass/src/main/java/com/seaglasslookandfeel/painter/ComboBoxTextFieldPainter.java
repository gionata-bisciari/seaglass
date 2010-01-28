/*
 * Copyright (c) 2009 Kathryn Huxtable and Kenneth Orr.
 *
 * This file is part of the SeaGlass Pluggable Look and Feel.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * $Id$
 */
package com.seaglasslookandfeel.painter;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;

import javax.swing.JComponent;

import com.seaglasslookandfeel.painter.AbstractRegionPainter.PaintContext.CacheMode;
import com.seaglasslookandfeel.painter.util.ShapeUtil;

/**
 * ComboBoxTextFieldPainter implementation.
 */
public final class ComboBoxTextFieldPainter extends AbstractRegionPainter {
    public static enum Which {
        BACKGROUND_DISABLED, BACKGROUND_ENABLED, BACKGROUND_SELECTED,
    }

    private static final Color DISABLED_BORDER = new Color(0xdddddd);
    private static final Color ENABLED_BORDER  = new Color(0xbbbbbb);
    private static final Color DARKER_SHADOW   = new Color(0xe1e1e1);
    private static final Color LIGHTER_SHADOW  = new Color(0xf5f5f5);

    private Which              state;
    private PaintContext       ctx;

    public ComboBoxTextFieldPainter(Which state) {
        super();
        this.state = state;
        this.ctx = new PaintContext(CacheMode.FIXED_SIZES);
    }

    @Override
    protected void doPaint(Graphics2D g, JComponent c, int width, int height, Object[] extendedCacheKeys) {
        switch (state) {
        case BACKGROUND_DISABLED:
            paintDisabled(g, c, width, height);
            break;
        case BACKGROUND_ENABLED:
            paintEnabled(g, c, width, height);
            break;
        case BACKGROUND_SELECTED:
            paintSelected(g, c, width, height);
            break;
        }
    }

    @Override
    protected PaintContext getPaintContext() {
        return ctx;
    }

    private void paintDisabled(Graphics2D g, JComponent c, int width, int height) {
        paintButton(g, c, width, height, DISABLED_BORDER);
    }

    private void paintEnabled(Graphics2D g, JComponent c, int width, int height) {
        paintButton(g, c, width, height, ENABLED_BORDER);
    }

    private void paintSelected(Graphics2D g, JComponent c, int width, int height) {
        paintButton(g, c, width, height, ENABLED_BORDER);
    }

    private void paintButton(Graphics2D g, JComponent c, int width, int height, Color borderColor) {
        Shape s = ShapeUtil.createRectangle(3, 3, width - 3, height - 6);
        g.setColor(c.getBackground());
        g.fill(s);

        paintInternalDropShadow(g, width, height);

        s = ShapeUtil.createRectangleNoRightSide(2, 2, width - 3, height - 5);
        g.setColor(borderColor);
        g.draw(s);
    }

    private void paintInternalDropShadow(Graphics2D g, int width, int height) {
        g.setColor(DARKER_SHADOW);
        g.drawLine(3, 3, width - 1, 3);
        g.setColor(LIGHTER_SHADOW);
        g.drawLine(3, 4, width - 1, 4);
    }
}
