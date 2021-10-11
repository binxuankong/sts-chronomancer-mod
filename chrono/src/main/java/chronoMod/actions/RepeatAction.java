package chronoMod.actions;

import chronoMod.patches.ChronoEnum;
import chronoMod.powers.RecallPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class RepeatAction extends AbstractGameAction {
    private AbstractPlayer p;

    public RepeatAction(int rewindCount) {
        this.p = AbstractDungeon.player;
        this.amount = rewindCount;
    }

    public void update() {
        ArrayList<AbstractPower> powers = p.powers;
        Collections.sort(powers);
        Iterator var1 = powers.iterator();

        while(var1.hasNext()) {
            AbstractPower pow = (AbstractPower)var1.next();
            if (pow.type == ChronoEnum.RECALL) {
                RecallPower recall_pow = (RecallPower)pow;
                for (int i = 0; i < this.amount; i++) {
                    recall_pow.flash();
                    recall_pow.recallEffect();
                }
                break;
            }
        }

        this.isDone = true;
    }
}
