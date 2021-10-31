package chronoMod.relics;

import chronoMod.ChronoMod;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class ChemicalXX extends AbstractRelic {
    private static final String RELIC_ID = ChemicalXX.class.getSimpleName();
    public static final String ID = ChronoMod.makeID(RELIC_ID);

    public ChemicalXX() {
        super(ID, "chemicalX.png", RelicTier.SPECIAL, LandingSound.CLINK);
    }

    @Override
    public void onEquip() {
        AbstractDungeon.shopRelicPool.remove("Chemical X");
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new ChemicalXX();
    }
}
