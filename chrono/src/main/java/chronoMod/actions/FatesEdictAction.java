package chronoMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class FatesEdictAction extends AbstractGameAction {
    private AbstractPlayer p;
    private AbstractMonster m;
    private DamageInfo info;
    private int block;

    public FatesEdictAction(AbstractPlayer p, AbstractMonster m, DamageInfo info, int block) {
        this.p = p;
        this.m = m;
        this.info = info;
        this.block = block;
    }

    public void update() {
        if (this.m != null && this.m.getIntentBaseDmg() >= 0) {
            this.addToBot(new GainBlockAction(this.p, this.p, this.block));
        } else {
            this.addToTop(new DamageAction(m, this.info, AttackEffect.SMASH));
        }
        this.isDone = true;
    }
}
