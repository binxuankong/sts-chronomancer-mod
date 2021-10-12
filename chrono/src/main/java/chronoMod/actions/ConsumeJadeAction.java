package chronoMod.actions;

import chronoMod.powers.ArcaneBlessingPower;
import chronoMod.relics.OldWine;
import chronoMod.relics.RubyAmulet;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class ConsumeJadeAction extends AbstractGameAction {
    private AbstractPlayer p;

    public ConsumeJadeAction() {
        this.p = AbstractDungeon.player;
    }

    public void update() {
        // Arcane Blessing
        if (this.p.hasPower(ArcaneBlessingPower.POWER_ID)) {
            this.p.getPower(ArcaneBlessingPower.POWER_ID).onSpecificTrigger();
        }

        // Ruby Amulet
        AbstractRelic rubyAmulet = this.p.getRelic(RubyAmulet.ID);
        if (rubyAmulet != null) {
            rubyAmulet.onTrigger();
        }

        // Old Wine
        AbstractRelic oldWine = this.p.getRelic(OldWine.ID);
        if (oldWine != null) {
            oldWine.onTrigger();
        }

        this.isDone = true;
    }
}
